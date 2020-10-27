import { CakeActionTypes, RootState } from '../types/types'
import { SET_CAKES } from '../actions'

const initialState: RootState = { cakes: [] }

export const cakesReducer = (
  state: RootState = initialState,
  action: CakeActionTypes
): RootState => {
  switch (action.type) {
    case SET_CAKES:
      return {
        ...state,
        cakes: action.payload
      }
    // case GET_CAKE_REVIEW:
    //   return {
    //     ...state,
    //     review: action.payload
    //   }
    default:
      return state
  }
}
