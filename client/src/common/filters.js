import tinytime from 'tinytime'

const tinyTimeOptions = {
    padMonth: true,
    padDays: true,
    padHours: true
};

export const date = function (value, noYear = false) {
    let format = noYear ? '{Mo}-{DD}' : '{YYYY}-{Mo}-{DD}';
    if (value) {
        return tinytime(format, tinyTimeOptions).render(new Date(value))
    } else {
        return '--'
    }
};

export const time = function (value) {
    let format = '{H}:{mm}:{ss}';
    if (value) {
        return tinytime(format, tinyTimeOptions).render(new Date(value))
    } else {
        return '--'
    }
};

export const datetime = function (value) {
    if (value) {
        let format = '{YYYY}-{Mo}-{DD} {H}:{mm}';
        return tinytime(format, tinyTimeOptions).render(new Date(value))
    } else {
        return '--'
    }
};

export const num = function (value) {
    return value < 10 ? '0' + value : value
};