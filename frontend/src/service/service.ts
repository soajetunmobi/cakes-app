import * as endpoints from '../../src/common/const/endpoints'
import { Review } from '../types/types'

export const API_BASE_URL = 'http://localhost:8080'

export const cakeService = {
  getAll,
  getById,
  update,
  delete: _delete,
  add
}

function getAll(): Promise<any> {
  const requestOptions = {
    baseURL: API_BASE_URL,
    method: 'GET'
  }
  return fetch(`${API_BASE_URL}/${endpoints.cakes()}`, requestOptions).then(handleResponse)
}

function getById(id: number): Promise<any> {
  const requestOptions = {
    baseURL: API_BASE_URL,
    method: 'GET'
  }
  return fetch(`${API_BASE_URL}/${endpoints.cake(id)}/review`, requestOptions).then(handleResponse)
}

//prefixed function name because delete is a reserved word in JS
function _delete(id: number): Promise<any> {
  const requestOptions = {
    baseURL: API_BASE_URL,
    method: 'DELETE'
  }
  return fetch(`${API_BASE_URL}/${endpoints.cake(id)}`, requestOptions).then(handleResponse)
}

function add(review: Review): Promise<any> {
  const requestOptions = {
    baseURL: API_BASE_URL,
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(review)
  }
  return fetch(`${API_BASE_URL}/${endpoints.cakes()}`, requestOptions).then(handleResponse)
}

function update(review: Review): Promise<any> {
  const requestOptions = {
    baseURL: API_BASE_URL,
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(review)
  }
  return fetch(`${API_BASE_URL}/${endpoints.cakes()}`, requestOptions).then(handleResponse)
}

function handleResponse(response: Response): Promise<any> {
  return response.text().then(text => {
    const data = text && JSON.parse(text)
    if (!response.ok) {
      const error = (data && data.message) || response.statusText
      return Promise.reject(error)
    }
    return data
  })
}
