import { NativeModules } from 'react-native'

const { ARNLocalNotifications } = NativeModules

const ArkReactNativeLocalNotifications = {
  createNotification: (id, title, text, datetime) => {
    ARNLocalNotifications.createNotification(id, title, text, datetime)
  },

  deleteNotification: (id) => {
    ARNLocalNotifications.deleteNotification(id)
  },

  updateNotification: (id, title, text, datetime) => {
    ARNLocalNotifications.updateNotification(id, title, text, datetime)
  },
}

export default ArkReactNativeLocalNotifications
