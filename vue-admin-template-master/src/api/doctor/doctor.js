import request from '@/utils/request'

export function getAllUser(doctorId) {
    return request({
        url: `/doctor/getAllUser/${doctorId}`,
        method: 'get'
    })
}

export function getAllHistory(doctorId, nickname) {
    return request({
        url: `/doctor/${doctorId}/${nickname}`,
        method: 'get'
    })
}

export function getAllergy(userId) {
    return request({
        url: `/doctor/allergy/${userId}`,
        method: 'get'
    })
}

export function addAllergy(userId, name, indication, property) {
    return request({
        url: `/doctor/allergy/${userId}`,
        method: 'post',
        data: {
            name,
            indication,
            property
        },
    })
}
