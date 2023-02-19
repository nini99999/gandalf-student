export default [
    {
        name: 'student',
        path: 'student',
        component: () => import('./student.vue')
    },
    {
        name: 'student-info',
        path: 'student/:id',
        component: () => import('./student-info.vue')
    }
]