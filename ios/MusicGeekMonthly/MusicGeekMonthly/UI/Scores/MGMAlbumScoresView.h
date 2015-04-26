//
//  MGMAlbumScoresView.h
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 04/11/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMTabbedView.h"

typedef NS_ENUM(NSUInteger, MGMAlbumScoresViewSelection)
{
    MGMAlbumScoresViewSelectionClassicAlbums,
    MGMAlbumScoresViewSelectionNewAlbums,
    MGMAlbumScoresViewSelectionAllAlbums
};

@protocol MGMAlbumScoresViewDelegate <NSObject>

- (void) selectionChanged:(MGMAlbumScoresViewSelection)selection;

@end

@interface MGMAlbumScoresView : MGMTabbedView

@property (weak) id<MGMAlbumScoresViewDelegate> delegate;

@property (readonly) UICollectionView *collectionView;

- (void) setSelection:(MGMAlbumScoresViewSelection)selection;

@end
