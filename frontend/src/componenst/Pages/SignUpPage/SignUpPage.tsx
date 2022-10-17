import { Formik } from 'formik';
import React, { FC, useState } from 'react';
import { Button, Card, Form, FormControl, FormGroup } from 'react-bootstrap';
import { toast } from 'react-toastify';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router-dom';
import * as yup from 'yup';
import { useAppDispatch } from '../../../hooks/defaultHooks';
import { useRegistrationMutation } from '../../../store/api/authApiSlice/authApiSlice';
import { login } from '../../../store/slices/authSlice';
import { openModal } from '../../../store/slices/modalSlice';
import { routes } from '../../../utils/routes';

const SignUpPage: FC = () => {
  const [alreadyRegistered, setAlreadyRegistered] = useState<boolean>(false);
  const [registration] = useRegistrationMutation();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { t } = useTranslation();
  const notify = () => toast.success(t('toast.registerSuccess'));
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
      <Formik
        initialValues={{
          email: '',
          password: '',
          confirmPassword: '',
        }}
        validateOnChange={false}
        validationSchema={validationSchema}
        onSubmit={async (values) => {
          const { email, password } = values;
          const emailInLowerCase = email.toLocaleLowerCase();
          try {
            const response = await registration({ email: emailInLowerCase, password }).unwrap();
            dispatch(login(response));
            setAlreadyRegistered(false);
            notify();
            navigate(routes.pages.mainPagePath());
            // eslint-disable-next-line @typescript-eslint/no-explicit-any
          } catch (e: any) {
            if (e?.status === 409) setAlreadyRegistered(true);
          }
        }}
      >
        {({ touched, values, handleChange, handleSubmit, isValid, errors, dirty, setErrors }) => {
          const onChangeHandle = (event: React.ChangeEvent) => {
            handleChange(event);
            setErrors({});
            setAlreadyRegistered(false);
          };
          return (
            <Form
              onSubmit={handleSubmit}
              className='col-12 col-md-6 mt-3 mt-mb-0 border p-5 border-primary rounded d-flex flex-column'
            >
              <h2 className='mb-4'>{t('registration.header')}</h2>
              <FormGroup className='form-floating mb-3'>
                <p>{t('registration.email')}:</p>
                <div className='container'>
                  <FormControl
                    type='text'
                    name='email'
                    id='email'
                    placeholder={t('registration.emailPlaceholder')}
                    onChange={onChangeHandle}
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
                    onChange={onChangeHandle}
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
                    type='password'
                    className='form-control'
                    name='confirmPassword'
                    id='confirmPassword'
                    placeholder={t('registration.confirmPlaceholder')}
                    onChange={onChangeHandle}
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
              <p>
                {t('registration.rules')}
                <Card.Link href='#' onClick={handleModalClick}>
                  {t('registration.rulesLink')}
                </Card.Link>
              </p>
              <Button
                variant='outline-primary'
                className='w-50 mx-auto mb-3 dblock'
                type='submit'
                disabled={!isValid && !dirty}
              >
                {t('registration.submit')}
              </Button>
              <div className='link text-center'>
                {t('registration.notNew')}{' '}
                <span>
                  <Link to='/login'>{t('registration.enter')}</Link>
                </span>
              </div>
            </Form>
          );
        }}
      </Formik>
    </div>
  );
};

export default SignUpPage;
