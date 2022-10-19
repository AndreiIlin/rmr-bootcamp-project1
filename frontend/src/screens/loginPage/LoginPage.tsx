import { useFormik } from 'formik';
import React, { FC, useEffect, useRef, useState } from 'react';
import { Button, Container, Form } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import * as yup from 'yup';
import { useAppDispatch } from '../../hooks/defaultHooks';
import { useLoginMutation } from '../../store/api/authApiSlice/authApiSlice';
import { login } from '../../store/slices/authSlice';
import { routes } from '../../utils/routes';

const LoginPage: FC = () => {
  const [userLogin] = useLoginMutation();
  const [disabled, setDisabled] = useState<boolean>(false);
  const [errorEmailMessage, setErrorEmailMessage] = useState('');
  const [errorPasswordMessage, setErrorPasswordMessage] = useState('');
  const emailRef = useRef<HTMLInputElement | null>(null);
  const passwordRef = useRef<HTMLInputElement | null>(null);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const validationSchema = yup.object().shape({
    email: yup.string().email(t('formErrors.invalidEmail')).required(t('formErrors.required')),
    password: yup
      .string()
      .min(8, t('formErrors.minPasswordLength'))
      .max(30, t('formErrors.maxPasswordLength'))
      .required(t('formErrors.required')),
  });

  const initialValues = {
    email: '',
    password: '',
  };

  const formik = useFormik({
    initialValues,
    validationSchema,
    validateOnChange: false,
    onSubmit: async (values) => {
      const { email, password } = values;
      const emailInLowerCase = email.toLocaleLowerCase();
      try {
        setErrorEmailMessage('');
        setErrorPasswordMessage('');
        const response = await userLogin({ email: emailInLowerCase, password }).unwrap();
        dispatch(login(response));
        navigate(routes.pages.mainPagePath());
        // eslint-disable-next-line @typescript-eslint/no-explicit-any
      } catch (error: any) {
        setDisabled(true);
        const { status } = error;
        if (status === 401) {
          console.log(123);
          setErrorPasswordMessage(t('login.failedPassword'));
        } else if (status === 404) {
          setErrorEmailMessage(t('login.failedEmail'));
        } else {
          toast.warning(t('toast.networkError'));
        }
      } finally {
        setDisabled(false);
      }
    },
  });

  const onChangeHandle = (event: React.ChangeEvent) => {
    formik.handleChange(event);
    formik.setErrors({});
    setDisabled(false);
  };

  useEffect(() => {
    emailRef.current?.select();
  }, [errorEmailMessage]);

  useEffect(() => {
    passwordRef.current?.select();
  }, [errorPasswordMessage]);

  useEffect(() => {
    emailRef.current?.focus();
  }, []);

  return (
    <Container className='my-5 d-flex justify-content-center align-items-center'>
      <Form
        onSubmit={formik.handleSubmit}
        className='col-12 col-md-6 mt-3 border p-5 border-primary rounded d-flex flex-column bg-light'
      >
        <h2 className='mb-4'>{t('login.header')}</h2>
        <Form.Group className='mb-3 position-relative'>
          <Form.Label>{t('login.email')}:</Form.Label>
          <Form.Control
            type='text'
            name='email'
            id='email'
            ref={emailRef}
            placeholder={t('login.emailPlaceholder')}
            onChange={onChangeHandle}
            isInvalid={!!formik.errors.email || !!errorEmailMessage}
            value={formik.values.email}
          />
          <Form.Control.Feedback type='invalid' tooltip>
            {formik.errors.email ?? errorEmailMessage}
          </Form.Control.Feedback>
        </Form.Group>
        <Form.Group className='mb-3 position-relative'>
          <Form.Label>{t('login.password')}: </Form.Label>
          <Form.Control
            type='password'
            className='form-control'
            name='password'
            id='password'
            ref={passwordRef}
            placeholder={t('login.passwordPlaceholder')}
            onChange={onChangeHandle}
            value={formik.values.password}
            isInvalid={!!formik.errors.password || !!errorPasswordMessage}
          />
          <Form.Control.Feedback type='invalid' tooltip>
            {formik.errors.password ?? errorPasswordMessage}
          </Form.Control.Feedback>
        </Form.Group>
        <Button
          variant='outline-primary'
          className='w-50 mx-auto my-3'
          type='submit'
          disabled={disabled}
        >
          {t('login.enter')}
        </Button>
        <div className='link text-center'>
          {t('login.newUser')}{' '}
          <span>
            <Link to='/signup'>{t('login.register')}</Link>
          </span>
        </div>
      </Form>
    </Container>
  );
};

export default LoginPage;
