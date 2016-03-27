//
//  NSLayoutConstraint+MGM.m
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 05/03/16.
//  Copyright © 2016 Ceri Hughes. All rights reserved.
//

#import "NSLayoutConstraint+MGM.h"

@implementation NSLayoutConstraint (MGM)

+ (instancetype)constraintWithItem:(id)view1
                         attribute:(NSLayoutAttribute)attr1
                         relatedBy:(NSLayoutRelation)relation
                            toItem:(nullable id)view2
                         attribute:(NSLayoutAttribute)attr2
                        multiplier:(CGFloat)multiplier
                          constant:(CGFloat)c
                          priority:(UILayoutPriority)priority
{
    NSLayoutConstraint *constraint = [self constraintWithItem:view1 attribute:attr1 relatedBy:relation toItem:view2 attribute:attr2 multiplier:multiplier constant:c];
    constraint.priority = priority;
    return constraint;
}

+ (NSArray<__kindof NSLayoutConstraint *> *)constraintsWithItem:(id)view1
                                   thatMatchCenterAndSizeOfItem:(id)view2
{
    return [self constraintsWithItem:view1
        thatMatchCenterAndSizeOfItem:view2
                            priority:UILayoutPriorityRequired];
}

+ (NSArray<__kindof NSLayoutConstraint *> *)constraintsWithItem:(id)view1
                                   thatMatchCenterAndSizeOfItem:(id)view2
                                                       priority:(UILayoutPriority)priority
{
    NSMutableArray<__kindof NSLayoutConstraint *> *constraints = [NSMutableArray array];

    [constraints addObject:[NSLayoutConstraint constraintWithItem:view1
                                                        attribute:NSLayoutAttributeCenterX
                                                        relatedBy:NSLayoutRelationEqual
                                                           toItem:view2
                                                        attribute:NSLayoutAttributeCenterX
                                                       multiplier:1
                                                         constant:0
                                                         priority:priority]];

    [constraints addObject:[NSLayoutConstraint constraintWithItem:view1
                                                        attribute:NSLayoutAttributeCenterY
                                                        relatedBy:NSLayoutRelationEqual
                                                           toItem:view2
                                                        attribute:NSLayoutAttributeCenterY
                                                       multiplier:1
                                                         constant:0
                                                         priority:priority]];

    [constraints addObject:[NSLayoutConstraint constraintWithItem:view1
                                                        attribute:NSLayoutAttributeWidth
                                                        relatedBy:NSLayoutRelationEqual
                                                           toItem:view2
                                                        attribute:NSLayoutAttributeWidth
                                                       multiplier:1
                                                         constant:0
                                                         priority:priority]];

    [constraints addObject:[NSLayoutConstraint constraintWithItem:view1
                                                        attribute:NSLayoutAttributeHeight
                                                        relatedBy:NSLayoutRelationEqual
                                                           toItem:view2
                                                        attribute:NSLayoutAttributeHeight
                                                       multiplier:1
                                                         constant:0
                                                         priority:priority]];

    return [constraints copy];
}

+ (NSArray<__kindof NSLayoutConstraint *> *)constraintsThatTetherViewToSuperview:(UIView *)view
{
    NSDictionary *views = NSDictionaryOfVariableBindings(view);

    NSMutableArray<__kindof NSLayoutConstraint *> *constraints = [NSMutableArray array];

    [constraints addObjectsFromArray:[NSLayoutConstraint constraintsWithVisualFormat:@"H:|[view]|"
                                                                             options:0
                                                                             metrics:nil
                                                                               views:views]];

    [constraints addObjectsFromArray:[NSLayoutConstraint constraintsWithVisualFormat:@"V:|[view]|"
                                                                             options:0
                                                                             metrics:nil
                                                                               views:views]];

    return [constraints copy];
}


@end