import React, { FunctionComponent } from 'react'
import { useHistory } from 'react-router-dom'
import { LoadingIndicator } from '../../components/LoadingIndicator'
import { ErrorPanel } from '../../components/ErrorPanel'
import { routes } from '../../common/const/routes'
import { useFetchCakeReview } from '../../hooks'
import { IHistory } from '../../types/types'

export const ViewReviewPage: FunctionComponent = () => {
  const { review, isLoading, error } = useFetchCakeReview()
  const history = useHistory<IHistory>()

  if (isLoading || !review) {
    return <LoadingIndicator />
  }

  if (error) {
    const errorMessage = <div>{error}</div>
    return <ErrorPanel children={errorMessage} />
  }

  function goBackToCakeList() {
    history.push(routes.cakes)
  }

  return (
    <div className='form-wrapper'>
      <h2 className='page__title' data-testid='page-title'>
        Cake Review
      </h2>
      <div className='review'>
        <div className='row'>
          <span className='cake__image'>
            <img alt={review.name} data-testid='image' src={review.imageUrl} />
          </span>
        </div>
        <div className='row'>
          <span className='cake__name' data-testid='name'>
            {review.name}
          </span>
        </div>
        <div className='row'>
          <span className='cake__comment' data-testid='comment'>
            {review.comment}
          </span>
        </div>

        <div className='row'>
          <label>Yum factor</label>
          <div data-testid='yum-factor-wrapper'>
            <span id='1' className={`circle${review.yumFactor === 1 ? ' active' : ''}`}>
              1
            </span>
            <span id='2' className={`circle${review.yumFactor === 2 ? ' active' : ''}`}>
              2
            </span>
            <span id='3' className={`circle${review.yumFactor === 3 ? ' active' : ''}`}>
              3
            </span>
            <span id='4' className={`circle${review.yumFactor === 4 ? ' active' : ''}`}>
              4
            </span>
            <span id='5' className={`circle${review.yumFactor === 5 ? ' active' : ''}`}>
              5
            </span>
          </div>
        </div>
        <div className='buttons-container buttons-container--flex-end'>
          <button onClick={() => goBackToCakeList()} className='button--primary'>
            Back
          </button>
        </div>
      </div>
    </div>
  )
}
