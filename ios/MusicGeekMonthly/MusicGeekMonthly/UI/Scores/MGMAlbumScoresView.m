//
//  MGMAlbumScoresView.m
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 04/11/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMAlbumScoresView.h"
#import "MGMAlbumScoresCollectionViewLayout.h"

@interface MGMAlbumScoresView ()

@property (readonly) UISegmentedControl* segmentedControl;
@property (readonly) MGMAlbumScoresCollectionViewLayout *collectionViewLayout;

@end

@implementation MGMAlbumScoresView

- (void) commonInit
{
    [super commonInit];

    self.backgroundColor = [UIColor whiteColor];

    _collectionViewLayout = [[MGMAlbumScoresCollectionViewLayout alloc] init];
    _collectionViewLayout.itemSize = CGSizeMake(200, 200);
    _collectionViewLayout.itemSpace = 20;
    _collectionView = [[UICollectionView alloc] initWithFrame:CGRectZero collectionViewLayout:_collectionViewLayout];
    _collectionView.decelerationRate = UIScrollViewDecelerationRateFast;
    _collectionView.backgroundColor = [UIColor clearColor];
    _collectionView.showsHorizontalScrollIndicator = NO;
    _collectionView.showsVerticalScrollIndicator = NO;
    [self addSubview:_collectionView];

    _segmentedControl = [[UISegmentedControl alloc] initWithItems:@[@"Classic Albums", @"New Albums", @"All Albums"]];
    [_segmentedControl addTarget:self action:@selector(controlChanged:) forControlEvents:UIControlEventValueChanged];
    [self addSubview:_segmentedControl];
}

- (void) controlChanged:(UISegmentedControl*)sender
{
    [self.delegate selectionChanged:(MGMAlbumScoresViewSelection)sender.selectedSegmentIndex];
}

- (void) setSelection:(MGMAlbumScoresViewSelection)selection
{
    self.segmentedControl.selectedSegmentIndex = selection;

    // Fire the delegate, as this won't happen automatically when setting.
    [self controlChanged:self.segmentedControl];
}

- (void)layoutSubviewsIphone
{
    [super layoutSubviewsIphone];

    self.collectionView.frame = CGRectMake(0, 200, self.frame.size.width, 200);
    [self.collectionView.collectionViewLayout invalidateLayout];
}

@end
