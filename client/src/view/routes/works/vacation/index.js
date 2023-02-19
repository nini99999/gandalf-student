export default [
    {
        name: 'vacation',
        path: 'vacation',
        component: () => import('./vacation.vue')
    },
    {
        name: 'vacation-info',
        path: 'vacation/:id',
        component: () => import('./vacation-info.vue')
    },
    {
        name: 'vacation-request',
        path: 'vacation-request',
        component: () => import('./request.vue')
    }
]