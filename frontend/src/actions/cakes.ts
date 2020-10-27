import {
  ADD_CAKE_REVIEW,
  DELETE_CAKE_REVIEW,
  GET_CAKE_REVIEW,
  SET_CAKES,
  UPDATE_CAKE_REVIEW
} from './constants'
import { Cake, CakeActionTypes, Review } from '../types/types'

export const setCakes = (cakes: Cake[]): CakeActionTypes => {
  return {
    type: SET_CAKES,
    payload: cakes
  }
}

export const getCakeReview = (id: number): CakeActionTypes => {
  return {
    type: GET_CAKE_REVIEW,
    payload: id
  }
}

export const addCakeReview = (newReview: Review): CakeActionTypes => {
  return {
    type: ADD_CAKE_REVIEW,
    payload: newReview
  }
}

export const updateCakeReview = (updatedReview: Review): CakeActionTypes => {
  return {
    type: UPDATE_CAKE_REVIEW,
    payload: updatedReview
  }
}

export const deleteCakeReview = (id: number): CakeActionTypes => {
  return {
    type: DELETE_CAKE_REVIEW,
    payload: id
  }
}
