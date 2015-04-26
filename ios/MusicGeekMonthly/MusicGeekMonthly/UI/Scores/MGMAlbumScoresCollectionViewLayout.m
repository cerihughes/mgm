//
//  MGMAlbumScoresCollectionViewLayout.m
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 26/04/15.
//  Copyright (c) 2015 Ceri Hughes. All rights reserved.
//

#import "MGMAlbumScoresCollectionViewLayout.h"

@implementation MGMAlbumScoresCollectionViewLayoutAttributes

- (id)copyWithZone:(NSZone *)zone
{
    MGMAlbumScoresCollectionViewLayoutAttributes *copy = [super copyWithZone:zone];
    copy.scrollPosition = self.scrollPosition;
    return copy;
}

- (BOOL)isEqual:(id)object
{
    if (object == self) {
        return YES;
    }

    if (!object || ![[object class] isEqual:[self class]]) {
        return NO;
    }

    MGMAlbumScoresCollectionViewLayoutAttributes *other = (id)object;
    if (other.scrollPosition != self.scrollPosition) {
        return NO;
    }

    return [super isEqual:object];
}

@end

@interface MGMAlbumScoresCollectionViewLayout ()

@property (readonly) NSMutableDictionary *cellAttributes;

@end

@implementation MGMAlbumScoresCollectionViewLayout

- (instancetype)init
{
    if (!(self = [super init])) {
        return nil;
    }

    _cellAttributes = [NSMutableDictionary new];

    return self;
}

- (void)setItemSize:(CGSize)itemSize
{
    if (CGSizeEqualToSize(_itemSize, itemSize)) {
        return;
    }

    _itemSize = itemSize;
    [self invalidateLayout];
}

- (void)setItemSpace:(CGFloat)itemSpace
{
    if (_itemSpace == itemSpace) {
        return;
    }

    _itemSpace = itemSpace;
    [self invalidateLayout];
}

- (void)prepareLayout
{
    [super prepareLayout];

    [self.cellAttributes removeAllObjects];

    CGSize collectionViewSize = self.collectionView.bounds.size;
    CGFloat contentOffsetCenterX = self.collectionView.contentOffset.x + collectionViewSize.width / 2;
    CGFloat totalItemWidth = self.itemSize.width + self.itemSpace;

    for (NSInteger i = 0; i < [self.collectionView numberOfItemsInSection:0]; i++) {
        NSIndexPath *indexPath = [NSIndexPath indexPathForItem:i inSection:0];
        MGMAlbumScoresCollectionViewLayoutAttributes *attributes = [MGMAlbumScoresCollectionViewLayoutAttributes
                                                                    layoutAttributesForCellWithIndexPath:indexPath];
        attributes.frame = CGRectMake(totalItemWidth * i,
                                      0,
                                      self.itemSize.width,
                                      self.itemSize.height);

        CGFloat offset = attributes.center.x - contentOffsetCenterX;
        CGFloat percentage = offset / totalItemWidth;
        attributes.scrollPosition = percentage;

        self.cellAttributes[indexPath] = attributes;
    }
}

- (CGSize)collectionViewContentSize
{
    NSInteger numberOfItems = [self.collectionView numberOfItemsInSection:0];
    CGSize collectionViewSize = self.collectionView.bounds.size;
    CGFloat totalItemWidth = self.itemSize.width + self.itemSpace;
    CGFloat itemWidth = totalItemWidth * numberOfItems;
    CGFloat totalWidth = itemWidth - self.itemSpace;
    return CGSizeMake(MAX(0, totalWidth), collectionViewSize.height);
}

- (NSArray *)layoutAttributesForElementsInRect:(CGRect)rect
{
    NSMutableArray *layoutAttributes = [NSMutableArray arrayWithCapacity:self.cellAttributes.count];
    for (NSIndexPath *indexPath in self.cellAttributes) {
        UICollectionViewLayoutAttributes *attributes = self.cellAttributes[indexPath];
        if (CGRectIntersectsRect(rect, attributes.frame)) {
            [layoutAttributes addObject:attributes];
        }
    }
    return layoutAttributes;
}

- (UICollectionViewLayoutAttributes *)layoutAttributesForItemAtIndexPath:(NSIndexPath *)indexPath
{
    return self.cellAttributes[indexPath];
}

@end
