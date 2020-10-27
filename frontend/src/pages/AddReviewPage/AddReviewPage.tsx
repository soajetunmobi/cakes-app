import React, { FormEvent, FunctionComponent, useState } from 'react'
import { useHistory } from 'react-router-dom'
import { routes } from '../../common/const/routes'
import { cakeService } from '../../service'
import { ErrorPanel } from '../../components/ErrorPanel'

export const AddReviewPage: FunctionComponent = () => {
  const history = useHistory()

  const [review, setReview] = useState({
    name: '',
    imageUrl: '',
    comment: '',
    yumFactor: 0
  })
  const [error, setError] = useState<string | undefined>(undefined)

  function handleChange(e: FormEvent<HTMLInputElement>) {
    const { name, value } = e.currentTarget
    setReview(review => ({ ...review, [name]: value }))
  }

  function handleCommentChange(e: FormEvent<HTMLTextAreaElement>) {
    const { name, value } = e.currentTarget

    if (value.length >= 5 || value.length <= 200) {
      setReview(review => ({ ...review, [name]: value }))
    }
  }

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    if (isNameValid() && isCommentValid() && isYumFactorValid() && isImageUrlValid()) {
      try {
        await cakeService.add(review)
        if (!error) {
          history.push(routes.cakes)
        }
      } catch (err) {
        console.log('error', err)
        setError('There was an error saving your data')
      }
    }
  }

  const isYumFactorValid = (): boolean => {
    return review.yumFactor >= 1 && review.yumFactor <= 5
  }

  const saveYumFactorValue = (yumFactor: number) => {
    setReview(review => ({ ...review, yumFactor: yumFactor }))
  }

  const goBackToCakeList = (): void => {
    history.push(routes.cakes)
  }

  function isNameValid(): boolean {
    return review.name.length > 3
  }

  function validateUrl(url: string): boolean {
    return /((ftp|https?):\/\/)?(www\.)?[a-z0-9\-\.]{3,}\.[a-z]{2,3}$/.test(url)
  }

  function isImageUrlValid(): boolean {
    return validateUrl(review.imageUrl) && review.imageUrl.length > 1
  }

  function isCommentValid(): boolean {
    return review.comment.length >= 5 && review.comment.length <= 200
  }

  return (
    <div className='form-wrapper'>
      {error && (
        <ErrorPanel>
          <div>{error}</div>
        </ErrorPanel>
      )}

      <h2 className='page__title'>Add comment</h2>
      <form name='form' className='form' onSubmit={handleSubmit}>
        <div className='form__group'>
          <label>Name</label>
          <input
            type='text'
            name='name'
            data-testid='review-name'
            value={review.name}
            required
            onChange={handleChange}
            onBlur={isNameValid}
            className={'form__input'}
          />
          {!isNameValid() && (
            <div data-testid='name-error' className='invalid-feedback'>
              Cake name is required
            </div>
          )}
        </div>
        <div className='form__group'>
          <label>Image url</label>
          <input
            type='text'
            name='imageUrl'
            data-testid='review-image'
            value={review.imageUrl}
            required
            onChange={handleChange}
            onBlur={isImageUrlValid}
            className={'form__input'}
          />
          {!isImageUrlValid() && (
            <div data-testid='image-error' className='invalid-feedback'>
              Image url is required
            </div>
          )}
        </div>
        <div className='form__group'>
          <label>Comment</label>
          <textarea
            rows={2}
            data-testid='review-comment'
            maxLength={200}
            required
            name='comment'
            value={review.comment}
            onChange={handleCommentChange}
            onBlur={isCommentValid}
            className={'form__textarea'}
          />
          {!isCommentValid() && (
            <div data-testid='comment-error' className='invalid-feedback'>
              Comment is required (At least 5 characters and at most 200 characters)
            </div>
          )}
        </div>
        <div className='form__group no-border'>
          <label>Yum factor</label>
          <div>
            <span
              id='1'
              data-testid='yum-1'
              className={`circle cursor--pointer${review.yumFactor === 1 ? ' active' : ''}`}
              onClick={() => saveYumFactorValue(1)}
            >
              1
            </span>
            <span
              id='2'
              data-testid='yum-2'
              className={`circle cursor--pointer${review.yumFactor === 2 ? ' active' : ''}`}
              onClick={() => saveYumFactorValue(2)}
            >
              2
            </span>
            <span
              id='3'
              data-testid='yum-3'
              className={`circle cursor--pointer${review.yumFactor === 3 ? ' active' : ''}`}
              onClick={() => saveYumFactorValue(3)}
            >
              3
            </span>
            <span
              id='4'
              data-testid='yum-4'
              className={`circle cursor--pointer${review.yumFactor === 4 ? ' active' : ''}`}
              onClick={() => saveYumFactorValue(4)}
            >
              4
            </span>
            <span
              id='5'
              data-testid='yum-5'
              className={`circle cursor--pointer${review.yumFactor === 5 ? ' active' : ''}`}
              onClick={() => saveYumFactorValue(5)}
            >
              5
            </span>
          </div>
          {!isYumFactorValid() && (
            <div data-testid='error-yumfactor' className='invalid-feedback'>
              Yum factor is required
            </div>
          )}
        </div>
        <div className='buttons-container buttons-container--space-between'>
          <button onClick={() => goBackToCakeList()} className='button--secondary'>
            Cancel
          </button>
          <button className='button--primary'>Submit</button>
        </div>
      </form>
    </div>
  )
}
