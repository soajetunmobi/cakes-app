import React, { FunctionComponent } from 'react'
import { useHistory } from 'react-router-dom'
import { LoadingIndicator } from '../../components/LoadingIndicator'
import { ErrorPanel } from '../../components/ErrorPanel'
import { routes } from '../../common/const/routes'
import { useFetchCakes } from '../../hooks'

const ADD_BUTTON_LABEL = 'Add comment'
const VIEW_BUTTON_LABEL = 'View comment'

export const HomePage: FunctionComponent = () => {
  const history = useHistory()

  const { error, isLoading, cakes } = useFetchCakes()

  if (isLoading || !cakes) {
    return <LoadingIndicator />
  }

  if (error) {
    const errorMessage = <div>{error}</div>
    return <ErrorPanel children={errorMessage} />
  }

  const renderCakes = () => {
    return (
      cakes &&
      cakes.map(cake => (
        <div className='cake cursor--pointer' key={cake.id}>
          <span className='cake__name'>{cake.name}</span>
          <span className='cake__image'>
            <img alt={cake.name} src={cake.imageUrl} />
          </span>
          <div className='buttons-container buttons-container--flex-end'>
            <button className='button--secondary' onClick={() => viewReview(cake.id)}>
              {VIEW_BUTTON_LABEL}
            </button>
          </div>
        </div>
      ))
    )
  }

  const addCake = () => {
    history.push(routes.add)
  }

  const viewReview = (id: number) => {
    const path = {
      pathname: `/cakes/${id}/review`,
      state: { id }
    }
    history.push(path)
  }

  return (
    <div>
      <h2 className='page__title'>Cakes</h2>
      <div className='cakes'>{cakes && renderCakes()}</div>
      <div className='buttons-container buttons-container--center margin--bottom--double'>
        <button className='button--primary button--w250' onClick={() => addCake()}>
          {ADD_BUTTON_LABEL}
        </button>
      </div>
    </div>
  )
}
