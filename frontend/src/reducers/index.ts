import { combineReducers } from 'redux'
import { cakesReducer } from './cakes'

const rootReducer = combineReducers({
  cakes: cakesReducer
})

export type CakeState = ReturnType<typeof rootReducer>

export default rootReducer
