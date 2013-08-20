//
//  MGMHttpDao.m
//  Music Geek Monthly
//
//  Created by Ceri Hughes on 10/08/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMHttpDao.h"

@interface MGMHttpDao ()

@property (strong) NSDateFormatter* jsonDateFormatter;

@end

@implementation MGMHttpDao

- (id) initWithCoreDataDao:(MGMCoreDataDao *)coreDataDao
{
    if (self = [super initWithCoreDataDao:coreDataDao])
    {
        self.jsonDateFormatter = [[NSDateFormatter alloc] init];
        self.jsonDateFormatter.dateFormat = @"yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    }
    return self;
}

- (BOOL) needsUrlRefresh:(NSString*)identifier
{
    NSError* error = nil;
    MGMNextUrlAccess* nextAccess = [self.coreDataDao fetchNextUrlAccessWithIdentifier:identifier error:&error];
    if (error == nil && nextAccess)
    {
        NSDate* nextAccessDate = nextAccess.date;
        NSLog(@"Next access date for %@ is %@.", identifier, nextAccessDate);
        return ([[NSDate date] earlierDate:nextAccessDate] == nextAccessDate);
    }
    NSLog(@"No next access date for %@.", identifier);
    return YES;
}

- (void) setNextUrlRefresh:(NSString*)identifier inDays:(NSUInteger)days
{
    NSDateComponents* components = [[NSDateComponents alloc] init];
    components.day = days;

    NSDate* now = [NSDate date];
    NSDate* then = [[NSCalendar currentCalendar] dateByAddingComponents:components toDate:now options:0];

    NSLog(@"Setting next access date for %@ to %@.", identifier, then);

    [self.coreDataDao persistNextUrlAccess:identifier date:then completion:^(NSError* error)
    {
        // Swallow
    }];
}

- (NSData*) contentsOfUrl:(NSString*)url error:(NSError**)error
{
    return [self contentsOfUrl:url withHttpHeaders:nil error:error];
}

- (NSData*) contentsOfUrl:(NSString*)url withHttpHeaders:(NSDictionary*)headers error:(NSError**)error
{
    NSString* encodedUrl = [url stringByAddingPercentEscapesUsingEncoding:NSASCIIStringEncoding];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:[NSURL URLWithString:encodedUrl] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:10];
    [request setHTTPMethod: @"GET"];
    [headers enumerateKeysAndObjectsUsingBlock:^(id key, id obj, BOOL *stop)
    {
        [request addValue:obj forHTTPHeaderField:key];
    }];

    NSURLResponse *urlResponse = nil;
    NSData* data = [NSURLConnection sendSynchronousRequest:request returningResponse:&urlResponse error:error];
    NSLog(@"[%@] = [%@]", url, [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding]);
    return data;
}

- (NSDate*) dateForJsonString:(NSString *)jsonString
{
    return [self.jsonDateFormatter dateFromString:jsonString];
}

@end
