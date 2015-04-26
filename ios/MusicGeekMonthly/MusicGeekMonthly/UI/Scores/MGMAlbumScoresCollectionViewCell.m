//
//  MGMAlbumScoresCollectionViewCell.m
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 26/04/15.
//  Copyright (c) 2015 Ceri Hughes. All rights reserved.
//

#import "MGMAlbumScoresCollectionViewCell.h"

#import "MGMAlbumView.h"

@implementation MGMAlbumScoresCollectionViewCell

- (void)setAlbumView:(MGMAlbumView *)albumView
{
    if (albumView != _albumView) {
        if (_albumView) {
            [_albumView removeFromSuperview];
        }

        _albumView = albumView;

        if (_albumView) {
            [self.contentView addSubview:albumView];
        }
    }
}

- (void)prepareForReuse
{
    [super prepareForReuse];

    self.albumView = nil;
}

@end
