//
//  MGMAlbumScoresCollectionViewLayout.h
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 26/04/15.
//  Copyright (c) 2015 Ceri Hughes. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MGMAlbumScoresCollectionViewLayoutAttributes : UICollectionViewLayoutAttributes

@property (nonatomic) CGFloat scrollPosition;

@end

@interface MGMAlbumScoresCollectionViewLayout : UICollectionViewLayout

@property (nonatomic) CGSize itemSize;
@property (nonatomic) CGFloat itemSpace;

@end
