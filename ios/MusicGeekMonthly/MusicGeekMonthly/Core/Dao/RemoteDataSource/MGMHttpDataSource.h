//
//  MGMHttpDataSource.h
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 14/02/2014.
//  Copyright (c) 2014 Ceri Hughes. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MGMHttpDataSource : NSObject

@property (readonly) BOOL hasReachability;

- (NSData*) contentsOfUrl:(NSString*)url error:(NSError**)error;
- (NSData*) contentsOfUrl:(NSString*)url withHttpHeaders:(NSDictionary*)headers error:(NSError**)error;

@end
