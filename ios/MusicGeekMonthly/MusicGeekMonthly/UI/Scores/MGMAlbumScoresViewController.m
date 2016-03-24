//
//  MGMAlbumScoresViewController.m
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 10/09/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMAlbumScoresViewController.h"

@import CoreData;

#import "MGMAlbumGridView.h"
#import "MGMCore.h"
#import "MGMCoreDataAccess.h"
#import "MGMCoreDataTableViewDataSource.h"
#import "MGMDao.h"
#import "MGMDaoData.h"
#import "MGMDaoOperation.h"
#import "MGMGridManager.h"
#import "MGMImageHelper.h"
#import "MGMUI.h"
#import "UIViewController+MGMAdditions.h"

@interface MGMAlbumScoresViewController () <MGMAlbumGridViewDelegate, MGMAlbumScoresViewDelegate>

@property (strong) NSArray* albumMoids;

@end

@implementation MGMAlbumScoresViewController

- (void) loadView
{
    MGMAlbumScoresView* scoresView = [[MGMAlbumScoresView alloc] initWithFrame:[self fullscreenRect]];
    scoresView.albumGridView.delegate = self;
    scoresView.delegate = self;

    self.view = scoresView;
}

- (void) viewDidLoad
{
    // Setup 25 albums so we can put them into "activity in progress" mode...
    NSUInteger albumCount = 25;
    [self.view.albumGridView setAlbumCount:albumCount detailViewShowing:YES];
    NSUInteger rowCount = mgm_isIpad() ? 4 : 2;
    CGFloat albumSize = self.view.albumGridView.frame.size.width / rowCount;
    NSArray* gridData = [MGMGridManager rectsForRowSize:rowCount defaultRectSize:albumSize count:albumCount];

    for (NSUInteger i = 0; i < albumCount; i++)
    {
        NSValue* value = [gridData objectAtIndex:i];
        CGRect frame = [value CGRectValue];
        [self.view.albumGridView setAlbumFrame:frame forRank:i + 1];
    }

    [self.view setSelection:MGMAlbumScoresViewSelectionClassicAlbums];
}

- (void) loadAlbumsForChoice:(NSInteger)choice
{
    [self.view.albumGridView setActivityInProgressForAllRanks:YES];

    [self dataForChoice:choice completion:^(MGMDaoData* data) {
        NSArray* albumMoids = data.data;
        NSError* fetchError = data.error;
        
        // Resize the album view for new data...
        NSUInteger albumCount = albumMoids.count;
        [self.view.albumGridView setAlbumCount:albumCount detailViewShowing:YES];
        NSUInteger rowCount = mgm_isIpad() ? 4 : 2;
        CGFloat albumSize = self.view.albumGridView.frame.size.width / rowCount;
        NSArray* gridData = [MGMGridManager rectsForRowSize:rowCount defaultRectSize:albumSize count:albumCount];
        
        for (NSUInteger i = 0; i < albumCount; i++)
        {
            NSValue* value = [gridData objectAtIndex:i];
            CGRect frame = [value CGRectValue];
            [self.view.albumGridView setAlbumFrame:frame forRank:i + 1];
        }
        
        [self.view.albumGridView setActivityInProgressForAllRanks:YES];
        
        if (fetchError && albumMoids)
        {
            [self logError:fetchError];
        }
        
        if (albumMoids)
        {
            self.albumMoids = albumMoids;
            [self reloadAlbums];
        }
        else
        {
            [self showError:fetchError];
        }
    }];
}

- (void) dataForChoice:(NSInteger)choice completion:(DAO_FETCH_COMPLETION)completion
{
    switch (choice) {
        case 0:
            [self.core.dao fetchAllClassicAlbums:completion];
            break;
        case 1:
            [self.core.dao fetchAllNewlyReleasedAlbums:completion];
            break;
        case 2:
            [self.core.dao fetchAllEventAlbums:completion];
        default:
            break;
    }
}

- (void) reloadAlbums
{
    __block NSUInteger position = 1;
    for (NSManagedObjectID* albumMoid in self.albumMoids)
    {
        MGMAlbum* album = [self.core.coreDataAccess mainThreadVersion:albumMoid];
        [self renderAlbum:album atPostion:position];
        position++;
    }
}

- (void) renderAlbum:(MGMAlbum*)album atPostion:(NSUInteger)position
{
    MGMAlbumView* albumView = [self.view.albumGridView albumViewForRank:position];
    [self displayAlbum:album inAlbumView:albumView rank:position completion:^(NSError* error) {
        [self.ui logError:error];
    }];
}

#pragma mark -
#pragma mark MGMAlbumGridViewDelegate

- (void) albumPressedWithRank:(NSUInteger)rank
{
    NSManagedObjectID* albumMoid = [self.albumMoids objectAtIndex:rank - 1];
    MGMAlbum* album = [self.core.coreDataAccess mainThreadVersion:albumMoid];
    [self.albumSelectionDelegate albumSelected:album];
}

- (void) detailPressedWithRank:(NSUInteger)rank
{
    NSManagedObjectID* albumMoid = [self.albumMoids objectAtIndex:rank - 1];
    MGMAlbum* album = [self.core.coreDataAccess mainThreadVersion:albumMoid];
    [self.albumSelectionDelegate detailSelected:album sender:self];
}

#pragma mark -
#pragma mark MGMAlbumScoresViewDelegate

- (void) selectionChanged:(MGMAlbumScoresViewSelection)selection
{
    [self loadAlbumsForChoice:selection];
}

@end
