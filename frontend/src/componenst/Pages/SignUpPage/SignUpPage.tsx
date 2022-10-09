import { Formik } from 'formik';
import React, { FC, useState } from 'react';
import { Button, Card, Form, FormControl, FormGroup, FormLabel, ModalBody } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { useRegistrationMutation } from '../../../store/api/authApiSlice/authApiSlice';
import { useAppDispatch } from '../../../hooks/defaultHooks';
import { login } from '../../../store/slices/authSlice';
import { routes } from '../../../utils/routes';
import Modal from '../../Modals';
import { closeModal, openModal } from '../../../store/slices/modalSlice';

const SignUpPage: FC = () => {
  const [agree, setAgree] = useState<boolean>(true);
  const [alreadyRegistered, setAlreadyRegistered] = useState<boolean>(false);
  const [registration] = useRegistrationMutation();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { t } = useTranslation();
  const validationSchema = yup.object().shape({
    email: yup.string().email(t('formErrors.invalidEmail')).required(t('formErrors.required')),
    password: yup
      .string()
      .min(8, t('formErrors.minPasswordLength'))
      .max(30, t('formErrors.maxPasswordLength'))
      .required(t('formErrors.required')),
    confirmPassword: yup
      .string()
      .test(
        'confirmPassword',
        t('registration.confirmPassword'),
        (password, context) => password === context.parent.password,
      )
      .required(t('formErrors.required')),
  });

  const handleModalClick = () => {
    dispatch(openModal({ type: 'appRules' }));
  };

  return (
    <div className='container d-flex justify-content-center align-items-center h-100 gap-5'>
      {agree ? (
        <Formik
          initialValues={{
            email: '',
            password: '',
            confirmPassword: '',
            confirmUserAgreement: false,
            processingOfPersonalData: false,
          }}
          validateOnBlur
          validationSchema={validationSchema}
          onSubmit={async (values) => {
            const { email, password, confirmUserAgreement, processingOfPersonalData } = values;
            if (!confirmUserAgreement || !processingOfPersonalData) {
              setAgree(!agree);
            } else {
              try {
                const response = await registration({ email, password }).unwrap();
                dispatch(login(response));
                setAlreadyRegistered(false);
                navigate(routes.appPagePath());
              } catch (e: any) {
                if (e?.status === 409) setAlreadyRegistered(true);
              }
            }
          }}>
          {({
            touched,
            values,
            handleChange,
            handleBlur,
            handleSubmit,
            isValid,
            errors,
            dirty,
          }) => (
            <Form
              onSubmit={handleSubmit}
              className='col-12 col-md-6 mt-3 mt-mb-0 border p-5 border-primary rounded d-flex flex-column'>
              <h2 className='mb-4'>{t('registration.header')}</h2>
              <FormGroup className='form-floating mb-3'>
                <p>{t('registration.email')}:</p>
                <div className='container'>
                  <FormControl
                    type='email'
                    name='email'
                    id='email'
                    placeholder={t('registration.emailPlaceholder')}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    isInvalid={!!errors.email}
                    value={values.email}
                  />
                  {(touched.email && errors.email) || touched.email ? (
                    <FormControl.Feedback type='invalid' tooltip>
                      {errors.email}
                    </FormControl.Feedback>
                  ) : null}
                </div>
              </FormGroup>
              <FormGroup className='form-floating mb-3'>
                <p>{t('registration.password')}:</p>
                <div className='container'>
                  <FormControl
                    type='password'
                    className='form-control'
                    name='password'
                    id='password'
                    placeholder={t('registration.passwordPlaceholder')}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.password}
                    isInvalid={!!errors.password}
                  />
                  {(touched.password && errors.password) || touched.password ? (
                    <FormControl.Feedback type='invalid' tooltip>
                      {errors.password}
                    </FormControl.Feedback>
                  ) : null}
                </div>
              </FormGroup>
              <FormGroup className='form-floating mb-3'>
                <p>{t('registration.confirm')}</p>
                <div className='container'>
                  <FormControl
                    type='confirmPassword'
                    className='form-control'
                    name='confirmPassword'
                    id='confirmPassword'
                    placeholder={t('registration.confirmPlaceholder')}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    value={values.confirmPassword}
                    isInvalid={!!errors.confirmPassword}
                  />
                  {(touched.confirmPassword && errors.confirmPassword) ||
                  touched.confirmPassword ? (
                    <FormControl.Feedback type='invalid' tooltip>
                      {errors.confirmPassword}
                    </FormControl.Feedback>
                  ) : null}
                </div>
              </FormGroup>
              {alreadyRegistered ? (
                <div className='text-danger'>{t('registration.alreadyRegistered')}</div>
              ) : null}
              <FormGroup className='mt-3 d-flex gap-3'>
                <Form.Check type='checkbox' id='confirmUserAgreement' onChange={handleChange} />
                <FormLabel>
                  {t('registration.rules')}{' '}
                  <Card.Link href='#' onClick={handleModalClick}>
                    {t('registration.rulesLink')}
                  </Card.Link>
                </FormLabel>
              </FormGroup>
              <FormGroup className='mt-3 gap-3 d-flex'>
                <Form.Check type='checkbox' id='processingOfPersonalData' onChange={handleChange} />
                <FormLabel>
                  {t('registration.personal')}{' '}
                  <Card.Link href='#' onClick={handleModalClick}>
                    {t('registration.personalLink')}
                  </Card.Link>
                </FormLabel>
              </FormGroup>
              <Button
                variant='outline-primary'
                className='w-50 mx-auto mb-3 dblock mt-5'
                type='submit'
                disabled={!isValid && !dirty}>
                {t('registration.submit')}
              </Button>
              <div className='link text-center'>
                {t('registration.notNew')}{' '}
                <span>
                  <Link to='/login'>{t('registration.enter')}</Link>
                </span>
              </div>
            </Form>
          )}
        </Formik>
      ) : (
        <div className='not-agree'>
          <p>{t('registration.notAgree')}</p>
          <Button type='button' onClick={() => setAgree(!agree)}>
            {t('registration.back')}
          </Button>
        </div>
      )}
      <Modal />
    </div>
  );
};

export default SignUpPage;
