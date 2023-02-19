export default [
    {
        name: 'permission/department',
        path: 'permission/department',
        component: () => import('./department.vue')
    },
    {
        name: 'permission/department-info',
        path: 'permission/department/:id',
        component: () => import('./department-info.vue')
    },
    {
        name: 'permission/person',
        path: 'permission/person',
        component: () => import('./person.vue')
    },
    {
        name: 'permission/person-info',
        path: 'permission/person-info/:id',
        component: () => import('./person-info.vue')
    },
    {
        name: 'permission/role',
        path: 'permission/role',
        component: () => import('./role.vue')
    },
    {
        name: 'permission/role-info',
        path: 'permission/role-info/:key',
        component: () => import('./role-info.vue')
    },
    {
        name: 'permission/department-limit',
        path: 'permission/department-limit',
        component: () => import('./department-limit.vue')
    },
    {
        name: 'permission/department-limit-info',
        path: 'permission/department-limit-info/:id',
        component: () => import('./department-limit-info.vue')
    }
]