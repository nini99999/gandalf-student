export default [
    {
        name: 'car',
        path: 'car',
        component: () => import('./car.vue')
    },
    {
        name: 'car-info',
        path: 'car/info/:id',
        component: () => import('./car-info.vue')
    },
    {
        name: 'car-operation',
        path: '/car-operation',
        component: () => import('./operation.vue')
    },
    {
        name: 'car-operation-info',
        path: '/car/operation/:id',
        component: () => import('./operation-info.vue')
    },
    {
        name: 'car-operation-add',
        path: '/car-operation-add/:id?',
        component: () => import('./operation-add.vue')
    },
    {
        name: 'car-statistics',
        path: '/car/statistics',
        component: () => import('./statistics.vue')
    },
    {
        name: 'car-request',
        path: '/car/request',
        component: () => import('./request.vue')
    },
    {
        name: 'car-request-list',
        path: '/car/request-list',
        component: () => import('./request-list.vue')
    },
    {
        name: 'car-request-info',
        path: '/car/request-info/:id',
        component: () => import('./request-info.vue')
    },
    {
        name: 'car-repair',
        path: '/car/repair/:id?',
        component: () => import('./repair.vue')
    }
]