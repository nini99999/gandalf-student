import root from './root.vue'
import dashboard from './dashboard'
import vacation from './vacation'
import car from './car'
import student from './student'
import permission from './permission'
import other from './other'

export default {
    path: '/works',
    component: root,
    children: [
        ...dashboard,
        ...vacation,
        ...car,
        ...student,
        ...permission,
        ...other
    ]
}