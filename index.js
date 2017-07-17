import { NativeModules } from 'react-native'

const { RNLocalNotifications } = NativeModules

const ReactNativeLocalNotifications = {
  createNotification: (id, title, text, datetime) => {
    RNLocalNotifications.createNotification(id, title, text, datetime)
  },

  deleteNotification: (id) => {
    RNLocalNotifications.deleteNotification(id)
  },

  updateNotification: (id, title, text, datetime) => {
    RNLocalNotifications.updateNotification(id, title, text, datetime)
  },
}

export default ReactNativeLocalNotifications
