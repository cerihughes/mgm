//
//  MGMAlbumMetadata.h
//  MusicGeekMonthly
//
//  Created by Ceri Hughes on 14/08/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreData/CoreData.h>

typedef enum
{
    MGMAlbumServiceTypeLastFm = 1,
    MGMAlbumServiceTypeSpotify = 2,
    MGMAlbumServiceTypeWikipedia = 4,
    MGMAlbumServiceTypeYouTube = 8
}
MGMAlbumServiceType;

@class MGMAlbum;

@interface MGMAlbumMetadata : NSManagedObject

@property (nonatomic, strong) NSString* value;
@property (nonatomic, strong) MGMAlbum* album;

@property (nonatomic) MGMAlbumServiceType serviceType;

@end