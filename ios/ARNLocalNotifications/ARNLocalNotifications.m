#import <UIKit/UIKit.h>
#import "RCTBridgeModule.h"

@interface ARNLocalNotifications : NSObject <RCTBridgeModule>
@end

@implementation ARNLocalNotifications

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(createNotification:(NSInteger *)id title:(NSString *)title text:(NSString *)text datetime:(NSString *)datetime)
{
    [self createAlarm:id title:title text:text datetime:datetime update:FALSE];
};

RCT_EXPORT_METHOD(deleteNotification:(NSInteger *)id)
{
    [self deleteAlarm:id];
};

RCT_EXPORT_METHOD(updateNotification:(NSInteger *)id title:(NSString *)title text:(NSString *)text datetime:(NSString *)datetime)
{
    [self createAlarm:id title:title text:text datetime:datetime update:TRUE];
};

- (void)createAlarm:(NSInteger *)id title:(NSString *)title text:(NSString *)text datetime:(NSString *)datetime update:(Boolean *)update {
    if(update){
        [self deleteAlarm:id];
    }

    NSDateFormatter *dateFormat = [[NSDateFormatter alloc] init];
    [dateFormat setDateFormat:@"yyyy-MM-dd HH:mm"];

    NSDate *fireDate = [dateFormat dateFromString:datetime];
    if ([[NSDate date]compare: fireDate] == NSOrderedAscending) {
        int applicationIconBadgeNumber = ((int)[[[UIApplication sharedApplication] scheduledLocalNotifications] count] + 1);

        NSMutableDictionary *userInfo = [[NSMutableDictionary alloc] init];
        [userInfo setValue:[NSNumber numberWithInteger:id] forKey:@"id"];
        [userInfo setValue:datetime forKey:@"datetime"];
        [userInfo setValue:text forKey:@"text"];
        [userInfo setValue:title forKey:@"title"];

        UILocalNotification *notification = [[UILocalNotification alloc] init];
        notification.alertAction = @"Open";
        notification.alertBody = text;
        notification.alertTitle = title;
        notification.applicationIconBadgeNumber = applicationIconBadgeNumber;
        notification.fireDate = fireDate;
        notification.soundName = @"alarm.caf";
        notification.timeZone = [NSTimeZone defaultTimeZone];
        notification.userInfo = userInfo;

        [[UIApplication sharedApplication] scheduleLocalNotification:notification];
    }
}

- (void)deleteAlarm:(NSInteger *)id {
    NSCalendar *calendar = [NSCalendar currentCalendar];
    NSInteger comps = (NSDayCalendarUnit | NSMonthCalendarUnit | NSYearCalendarUnit);

    for (UILocalNotification * notification in [[UIApplication sharedApplication] scheduledLocalNotifications]) {
        NSMutableDictionary *userInfo = [notification userInfo];

        if ([[userInfo valueForKey:@"id"] integerValue] == [[NSNumber numberWithInteger:id] integerValue]) {
            [[UIApplication sharedApplication] cancelLocalNotification:notification];
        }
    }
}

@end
