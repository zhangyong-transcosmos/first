import request from '@/utils/request'

export function getInfo(token) {
  return request({
    url: '/users/info',
    method: 'get',
    params: { token }
  })
}

export function getList(params) {
  return request({
    url: '/users',
    method: 'get',
    params
  })
}

export function add(params) {
  return request({
    url: '/users',
    method: 'post',
    data: params
  })
}

export function update(id, params) {
  return request({
    url: '/users/' + id,
    method: 'put',
    data: params
  })
}

export function remove(id) {
  return request({
    url: '/users/' + id,
    method: 'delete'
  })
}

export function get(id) {
  return request({
    url: '/users/' + id,
    method: 'get'
  })
}
