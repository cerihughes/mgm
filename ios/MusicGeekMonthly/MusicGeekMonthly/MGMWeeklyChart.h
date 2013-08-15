//
//  MGMWeeklyChart.h
//  Music Geek Monthly
//
//  Created by Ceri Hughes on 10/08/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import <CoreData/CoreData.h>
#import "MGMTimePeriod.h"
#import "MGMChartEntry.h"

@interface MGMWeeklyChart : NSManagedObject

@property (nonatomic, strong) NSDate* startDate;
@property (nonatomic, strong) NSDate* endDate;
@property (nonatomic, strong) NSOrderedSet* chartEntries;

@end

@interface MGMWeeklyChart (ChartEntriesAccessors)

- (NSMutableOrderedSet*)primitiveChartEntries;
- (void) addChartEntriesObject:(MGMChartEntry*)value;

@end
