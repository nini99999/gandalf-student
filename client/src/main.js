import adminUser from './model/AdminUser'
import view from './view'

adminUser.checkOnline().then(function (online) {
    view(!online);
});
