
//
//  MGMHomeViewController.m
//  Music Geek Monthly
//
//  Created by Ceri Hughes on 04/07/2013.
//  Copyright (c) 2013 Ceri Hughes. All rights reserved.
//

#import "MGMHomeViewController.h"

#import "MGMBackgroundAlbumArtFetcher.h"
#import "MGMEvent.h"
#import "MGMPulsatingAlbumsView.h"

@interface MGMHomeViewController () <MGMBackgroundAlbumArtFetcherDelegate>

@property (strong) IBOutlet MGMPulsatingAlbumsView* albumsView;
@property (strong) IBOutlet UILabel* nextEventDateLabel;

@property (strong) MGMBackgroundAlbumArtFetcher* artFetcher;
@property (strong) MGMEvent* event;

- (IBAction) previousEventsPressed:(id)sender;
- (IBAction) chartsPressed:(id)sender;

@end

#define NEXT_EVENT_PATTERN @"The next event will be on %@ at %@"

@implementation MGMHomeViewController

- (id) init
{
    if (self = [super initWithNibName:nil bundle:nil])
    {
    }
    return self;
}

- (void) viewDidLoad
{
    [super viewDidLoad];
    
    [self.albumsView setupAlbumsInRow:4];

    [self.core.daoFactory.eventsDao fetchLatestEvent:^(MGMEvent* fetchedEvent, NSError* fetchError)
    {
        self.event = fetchedEvent;
        if (fetchError)
        {
            [self handleError:fetchError];
            return;
        }

        dispatch_async(dispatch_get_main_queue(), ^
        {
            // ... but update the UI in the main thread...
            [self displayEvent:self.event];
        });
    }];
}

- (void) viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    [self loadImages];
}

- (void) displayEvent:(MGMEvent*)event
{
    [super displayEvent:event];
    
    if (event.eventDate)
    {
        NSDateFormatter* eventDateFormatter = [[NSDateFormatter alloc] init];

        [eventDateFormatter setTimeStyle:NSDateFormatterNoStyle];
        [eventDateFormatter setDateStyle:NSDateFormatterMediumStyle];
        NSString* dateString = [eventDateFormatter stringFromDate:event.eventDate];

        [eventDateFormatter setDateStyle:NSDateFormatterNoStyle];
        [eventDateFormatter setTimeStyle:NSDateFormatterMediumStyle];
        NSString* timeString = [eventDateFormatter stringFromDate:event.eventDate];

        self.nextEventDateLabel.text = [NSString stringWithFormat:NEXT_EVENT_PATTERN, dateString, timeString];
    }    
}

- (void) loadImages
{
    if (self.artFetcher == nil)
    {
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^
        {
            // Search in a background thread...
            [self.core.daoFactory.lastFmDao fetchLatestTimePeriod:^(MGMTimePeriod* fetchedTimePeriod, NSError* timePeriodFetchError)
            {
                if (timePeriodFetchError)
                {
                    [self handleError:timePeriodFetchError];
                    return;
                }

                [self.core.daoFactory.lastFmDao fetchWeeklyChartForStartDate:fetchedTimePeriod.startDate endDate:fetchedTimePeriod.endDate completion:^(MGMWeeklyChart* fetchedWeeklyChart, NSError* weeklyChartFetchError)
                {
                    if (weeklyChartFetchError)
                    {
                        [self handleError:weeklyChartFetchError];
                        return;
                    }

                    self.artFetcher = [[MGMBackgroundAlbumArtFetcher alloc] initWithChartEntries:[fetchedWeeklyChart fetchChartEntries]];
                    self.artFetcher.lastFmDao = self.core.daoFactory.lastFmDao;
                    self.artFetcher.delegate = self;
                    [self renderImages];
                }];
            }];
        });
    }
    else
    {
        [self renderImages];
    }
}

- (void) renderImages
{
    for (int i = 0; i < self.albumsView.albumCount; i++)
    {
        [self.artFetcher generateImageAtIndex:i];

        // Sleep for a bit to give a tiling effect...
        [NSThread sleepForTimeInterval:0.25];
    }
}

- (IBAction) previousEventsPressed:(id)sender
{
    [self.delegate optionSelected:MGMHomeViewControllerOptionPreviousEvents];
}

- (IBAction) chartsPressed:(id)sender
{
    [self.delegate optionSelected:MGMHomeViewControllerOptionCharts];
}

#pragma mark -
#pragma mark MGMBackgroundAlbumArtFetcherDelegate

- (void) artFetcher:(MGMBackgroundAlbumArtFetcher*)fetcher renderImage:(UIImage*)image atIndex:(NSUInteger)index
{
    if (image == nil)
    {
        image = [UIImage imageNamed:@"album1.png"];
    }

    [self.albumsView renderImage:image atIndex:index];
}

- (void) artFetcher:(MGMBackgroundAlbumArtFetcher*)fetcher errorOccured:(NSError*)error
{
    [self handleError:error];
}

@end
