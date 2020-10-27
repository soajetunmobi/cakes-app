import {
  ADD_CAKE_REVIEW,
  DELETE_CAKE_REVIEW,
  GET_CAKE_REVIEW,
  SET_CAKES,
  UPDATE_CAKE_REVIEW
} from '../actions'

interface Cake {
  id: number
  name: string
  imageUrl: string
}

interface Review extends Cake {
  comment: string
  yumFactor: number
  id?: number
}

type RootState = {
  cakes: Cake[]
  review?: Review
}

interface GetCakesAction {
  type: typeof SET_CAKES
  payload: Cake[]
}

interface GetCakeReviewAction {
  type: typeof GET_CAKE_REVIEW
  payload: number
}

interface AddCakeReviewAction {
  type: typeof ADD_CAKE_REVIEW
  payload: Review
}

interface UpdateCakeReviewAction {
  type: typeof UPDATE_CAKE_REVIEW
  payload: Review
}

interface DeleteCakeReviewAction {
  type: typeof DELETE_CAKE_REVIEW
  payload: number
}

interface IHistory {
  id: number
}

export type CakeActionTypes =
  | GetCakesAction
  | GetCakeReviewAction
  | AddCakeReviewAction
  | UpdateCakeReviewAction
  | DeleteCakeReviewAction

type DispatchType = (args: CakeActionTypes) => CakeActionTypes
