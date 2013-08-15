
#import "MGMCore.h"

@interface MGMCore ()

- (void) createInstances;

@end

@implementation MGMCore

- (id) init
{
    if (self = [super init])
    {
        [self createInstances];
    }
    return self;
}

- (void) createInstances
{
    self.daoFactory = [[MGMDaoFactory alloc] init];
    self.albumPlayer = [[MGMAlbumPlayer alloc] init];
    self.albumPlayer.daoFactory = self.daoFactory;
}

- (void) enteredForeground
{
}

- (void) enteringBackground
{
}

- (void) keepAlive
{
}

@end