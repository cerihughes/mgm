//
//  MGMAlbumScoresViewController.m
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 10/09/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMAlbumScoresViewController.h"

#import "MGMAlbumScoresView.h"
#import "MGMCoreDataTableViewDataSource.h"
#import "MGMGridManager.h"
#import "MGMImageHelper.h"
#import "MGMAlbumScoresCollectionViewCell.h"

@interface MGMAlbumScoresViewController () <UICollectionViewDataSource, UICollectionViewDelegate, MGMAlbumScoresViewDelegate>

@property (strong) NSArray* albumMoids;

@end

@implementation MGMAlbumScoresViewController

#define SCORES_VIEW_CELL_ID @"MGMAlbumScoresViewControllerCellId"

- (void) loadView
{
    MGMAlbumScoresView* scoresView = [[MGMAlbumScoresView alloc] initWithFrame:[self fullscreenRect]];
    scoresView.collectionView.delegate = self;
    scoresView.collectionView.dataSource = self;
    scoresView.delegate = self;

    self.view = scoresView;
}

- (void) viewDidLoad
{
    MGMAlbumScoresView* scoresView = (MGMAlbumScoresView*)self.view;

    [scoresView.collectionView registerClass:[MGMAlbumScoresCollectionViewCell class] forCellWithReuseIdentifier:SCORES_VIEW_CELL_ID];
    [scoresView setSelection:MGMAlbumScoresViewSelectionClassicAlbums];
}

- (void) loadAlbumsForChoice:(NSInteger)choice
{
    [self dataForChoice:choice completion:^(MGMDaoData* data) {
        NSArray* albumMoids = data.data;
        NSError* fetchError = data.error;
        
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

- (void)reloadAlbums
{
    MGMAlbumScoresView* scoresView = (MGMAlbumScoresView*)self.view;
    [scoresView.collectionView reloadData];
}

#pragma mark - UICollectionViewDataSource

- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section
{
    return self.albumMoids.count;
}

- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath
{
    NSUInteger index = indexPath.row;
    NSManagedObjectID* albumMoid = self.albumMoids[index];
    MGMAlbum* album = [self.core.coreDataAccess mainThreadVersion:albumMoid];

    MGMAlbumScoresCollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:SCORES_VIEW_CELL_ID forIndexPath:indexPath];
    MGMAlbumView *albumView = [[MGMAlbumView alloc] initWithFrame:CGRectMake(25, 25, 150, 150)];
    cell.albumView = albumView;
    cell.backgroundColor = [UIColor purpleColor];

    albumView.activityInProgress = YES;
    [self displayAlbum:album inAlbumView:albumView rank:index + 1 completion:^(NSError* error) {
        albumView.activityInProgress = NO;
        [self.ui logError:error];
    }];

    return cell;
}

#pragma mark - UICollectionViewDelegate

- (void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath;
{
    NSManagedObjectID* albumMoid = [self.albumMoids objectAtIndex:indexPath.row - 1];
    MGMAlbum* album = [self.core.coreDataAccess mainThreadVersion:albumMoid];
    [self.albumSelectionDelegate albumSelected:album];
}

#pragma mark - MGMAlbumScoresViewDelegate

- (void) selectionChanged:(MGMAlbumScoresViewSelection)selection
{
    [self loadAlbumsForChoice:selection];
}

@end
