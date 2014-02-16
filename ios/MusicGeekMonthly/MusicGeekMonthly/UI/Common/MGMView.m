
#import "MGMView.h"

@implementation MGMView

static BOOL _isIpad;

+ (void) initialize
{
    _isIpad = ([UIDevice currentDevice].userInterfaceIdiom == UIUserInterfaceIdiomPad);
}

+ (UILabel*) boldTitleLabelWithText:(NSString *)text
{
    return [self labelWithText:text fontName:DEFAULT_FONT_BOLD size:[self titleTextSize]];
}

+ (UILabel*) boldSubtitleLabelWithText:(NSString *)text
{
    return [self labelWithText:text fontName:DEFAULT_FONT_BOLD size:[self subtitleTextSize]];
}

+ (UILabel*) italicTitleLabelWithText:(NSString *)text
{
    return [self labelWithText:text fontName:DEFAULT_FONT_ITALIC size:[self titleTextSize]];
}

+ (UILabel*) italicSubtitleLabelWithText:(NSString *)text
{
    return [self labelWithText:text fontName:DEFAULT_FONT_ITALIC size:[self subtitleTextSize]];
}

+ (CGFloat) titleTextSize
{
    return (_isIpad ? 28.0 : 17.0);
}

+ (CGFloat) subtitleTextSize
{
    return (_isIpad ? 20.0 : 13.0);
}

+ (UILabel*) labelWithText:(NSString *)text fontName:(NSString*)fontName size:(CGFloat)size
{
    UILabel* label = [[UILabel alloc] initWithFrame:CGRectZero];
    label.text = text;
    label.textAlignment = NSTextAlignmentCenter;
    label.textColor = [UIColor blackColor];
    label.backgroundColor = [UIColor clearColor];
    label.font = [UIFont fontWithName:fontName size:size];
    return label;
}

+ (UIButton*) buttonWithText:(NSString*)text image:(UIImage*)image;
{
    UIButton* button = [UIButton buttonWithType:UIButtonTypeCustom];
    button.titleLabel.font = [UIFont fontWithName:DEFAULT_FONT_MEDIUM size:(_isIpad ? 20.0 : 12.0)];
    if (image)
    {
        [button setBackgroundImage:image forState:UIControlStateNormal];
    }
    if (text)
    {
        [button setTitle:text forState:UIControlStateNormal];
    }
    return button;
}

+ (UIButton*) shadowedButtonWithText:(NSString*)text image:(UIImage*)image;
{
    UIButton* button = [self buttonWithText:text image:image];
    [button setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [button setTitleShadowColor:[UIColor blackColor] forState:UIControlStateNormal];
    button.titleLabel.shadowOffset = CGSizeMake(2, 2);
    return button;
}

- (BOOL) ipad
{
    return _isIpad;
}

- (CGFloat) statusBarHeight
{
    return 20.0;
}

- (CGFloat) navigationBarHeight
{
    return 44.0;
}

- (CGFloat) tabBarHeight
{
    if (self.ipad)
    {
        return 56.0;
    }
    else
    {
        return 49.0;
    }
}

- (void) commonInit
{
    if (self.ipad)
    {
        [self commonInitIpad];
    }
    else
    {
        [self commonInitIphone];
    }
}

- (void) commonInitIphone
{
    // Override
}

- (void) commonInitIpad
{
    // Override
}

- (id) initWithFrame:(CGRect)frame
{
    if (self = [super initWithFrame:frame])
    {
        [self commonInit];
    }
    return self;
}

- (id) initWithCoder:(NSCoder *)aDecoder
{
    if (self = [super initWithCoder:aDecoder])
    {
        [self commonInit];
    }
    return self;
}

- (void) layoutSubviews
{
    if (self.ipad)
    {
        [self layoutSubviewsIpad];
    }
    else
    {
        [self layoutSubviewsIphone];
    }
}

- (void) layoutSubviewsIphone
{
    // Override
}

- (void) layoutSubviewsIpad
{
    // Override
}

@end