import React, { FC } from 'react';
import { Formik } from 'formik';
import * as yup from 'yup';
import { Form, Button, FormControl, FormLabel, FormGroup } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const LoginPage: FC = () => {
  const validationSchema = yup.object().shape({
    email: yup.string().email().required(),
    password: yup.string().required(),
  });
  return (
    <div className='container d-flex justify-content-center align-items-center h-100 gap-5'>
      <Formik
        initialValues={{ email: '', password: '' }}
        validateOnBlur
        validationSchema={validationSchema}
        onSubmit={(values) => console.log(values)}>
        {({ touched, values, handleChange, handleBlur, handleSubmit, isValid, errors, dirty }) => (
          <Form
            onSubmit={handleSubmit}
            className='col-12 col-md-6 mt-3 mt-mb-0 border p-5 border-primary rounded d-flex flex-column'>
            <h2 className='mb-4'>Вход в приложение</h2>
            <FormGroup className='form-floating mb-3'>
              <p>Email:</p>
              <FormControl
                type='email'
                className='form-control'
                name='email'
                id='email'
                placeholder='Введите email'
                onChange={handleChange}
                onBlur={handleBlur}
                value={values.email}
              />
              {touched && errors.email ? <FormLabel>{errors.email}</FormLabel> : null}
            </FormGroup>
            <FormGroup className='form-floating mb-3'>
              <p>Пароль:</p>
              <FormControl
                type='password'
                className='form-control'
                name='password'
                id='password'
                placeholder='Введите пароль'
                onChange={handleChange}
                onBlur={handleBlur}
                value={values.password}
              />
              {touched && errors.password ? <FormLabel>{errors.password}</FormLabel> : null}
            </FormGroup>
            <Button
              variant='outline-primary'
              className='w-50 mx-auto mb-3 dblock'
              type='submit'
              disabled={!isValid && !dirty}>
              Войти
            </Button>
            <div className='link text-center'>
              Нет аккаунта?{' '}
              <span>
                <Link to='/signup'>Зарегистрироваться</Link>
              </span>
            </div>
          </Form>
        )}
      </Formik>
    </div>
  );
};

export default LoginPage;
