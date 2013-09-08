//
//  MGMEventTableViewDataSource.m
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 18/08/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMEventTableViewDataSource.h"

#import "MGMEvent.h"
#import "MGMEventTableCell.h"
#import "MGMImageHelper.h"

@implementation MGMEventTableViewDataSource

- (UITableViewCell*) tableView:(UITableView*)tableView cellForRowAtIndexPath:(NSIndexPath*)indexPath
{
    MGMEventTableCell* cell = (MGMEventTableCell*) [tableView dequeueReusableCellWithIdentifier:self.cellId];
    if (cell == nil)
    {
        cell = [[MGMEventTableCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:self.cellId];
    }

    cell.classicAlbumImageView.image = nil;
    cell.newlyReleasedAlbumImageView.image = nil;
    [cell.classicAlbumActivityView startAnimating];
    [cell.newlyReleasedAlbumActivityView startAnimating];

    MGMEvent* event = [self.fetchedResultsController objectAtIndexPath:indexPath];
    cell.eventTextLabel.text = event.groupValue;

    [self addAlbumImage:event.classicAlbum.objectID toImageView:cell.classicAlbumImageView withActivityView:cell.classicAlbumActivityView inCell:cell];
    [self addAlbumImage:event.newlyReleasedAlbum.objectID toImageView:cell.newlyReleasedAlbumImageView withActivityView:cell.newlyReleasedAlbumActivityView inCell:cell];

    return cell;
}

- (void) addAlbumImage:(NSManagedObjectID*)albumMoid toImageView:(UIImageView*)imageView withActivityView:(UIActivityIndicatorView*)activityIndicatorView inCell:(MGMEventTableCell*)cell
{
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^
    {
        MGMAlbum* album = [self.daoFactory.coreDataDao threadVersion:albumMoid];
        if ([album searchedServiceType:MGMAlbumServiceTypeLastFm] == NO)
        {
            [self.daoFactory.lastFmDao updateAlbumInfo:album completion:^(MGMAlbum* updatedAlbum, NSError* updateError)
            {
                if (updateError == nil)
                {
                    [self addAlbum:albumMoid toImageView:imageView withActivityView:activityIndicatorView inCell:cell];
                }
            }];
        }
        else
        {
            [self addAlbum:albumMoid toImageView:imageView withActivityView:activityIndicatorView inCell:cell];
        }
    });
}

- (void) addAlbum:(NSManagedObjectID*)albumMoid toImageView:(UIImageView*)imageView withActivityView:(UIActivityIndicatorView*)activityIndicatorView inCell:(MGMEventTableCell*)cell
{
    MGMAlbum* album = [self.daoFactory.coreDataDao threadVersion:albumMoid];
    NSString* albumArtUri = [album bestTableImageUrl];
    if (albumArtUri)
    {
        [MGMImageHelper asyncImageFromUrl:albumArtUri completion:^(UIImage* image, NSError* error)
        {
            dispatch_async(dispatch_get_main_queue(), ^
            {
                [activityIndicatorView stopAnimating];
                if (error == nil)
                {
                    imageView.image = image;
                }
                else
                {
                    imageView.image = [UIImage imageNamed:@"album1.png"];
                }
                [cell setNeedsDisplay];
            });
        }];
    }
    else
    {
        dispatch_async(dispatch_get_main_queue(), ^
        {
            [activityIndicatorView stopAnimating];
            imageView.image = [UIImage imageNamed:@"album1.png"];
            [cell setNeedsDisplay];
        });
    }
}

@end
