import { NativeModules } from 'react-native'

const { RNLocalNotifications } = NativeModules

const ReactNativeLocalNotifications = {
  createNotification: (id, text, datetime, sound) => {
    RNLocalNotifications.createNotification(id, text, datetime, sound)
  },

  deleteNotification: (id) => {
    RNLocalNotifications.deleteNotification(id)
  },

  updateNotification: (id, text, datetime, sound) => {
    RNLocalNotifications.updateNotification(id, text, datetime, sound)
  },
}

export default ReactNativeLocalNotifications
