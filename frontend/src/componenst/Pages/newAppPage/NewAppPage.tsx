import { Formik } from 'formik';
import React from 'react';
import { Button, Form, FormControl, FormGroup } from 'react-bootstrap';
import FormCheckLabel from 'react-bootstrap/esm/FormCheckLabel';
import * as yup from 'yup';
import { AddApp } from '../../../models/services/app';
import { useAddNewAppMutation } from '../../../store/api/appsApiSlice/appsApiSlice';

const initialValues: AddApp = {
  appName: '',
  appDescription: '',
  featurePrice: '',
  bugPrice: '',
  available: true,
  iconImage: '',
  downloadLink: '',
};

const validationSchema = yup.object().shape({
  appName: yup.string().min(3).max(30).required(),
  appDescription: yup.string().min(1).max(5000).required(),
  featurePrice: yup.number().required(),
  bugPrice: yup.number().required(),
  iconImage: yup.string().url().required(),
  downloadLink: yup.string().url().required(),
});

const NewAppPage = () => {
  const [addApp] = useAddNewAppMutation();
  return (
    <main className='app'>
      <div className='container d-flex justify-content-center align-items-center h-100'>
        <Formik
          initialValues={initialValues}
          validationSchema={validationSchema}
          onSubmit={async (values) => {
            const {
              appName,
              appDescription,
              available,
              featurePrice,
              bugPrice,
              iconImage,
              downloadLink,
            } = values;
            console.log(Number(featurePrice));
            const newApp = {
              appName,
              appDescription,
              featurePrice: Number(featurePrice),
              bugPrice: Number(bugPrice),
              available,
              iconImage,
              downloadLink,
              contractId: null,
            };
            try {
              const response = await addApp(newApp);
              console.log(response);
            } catch (e) {
              console.log(e);
            }
          }}>
          {({ errors, handleChange, handleSubmit, values }) => (
            <Form
              onSubmit={handleSubmit}
              className='col-xs-12 col-md-6 mt-3 mt-mb-0 border p-5 border-secondary rounded d-flex flex-column w-100 gap-3'>
              <h1>Добавить новое приложение</h1>
              <FormGroup>
                <p>Введите название приложения:</p>
                <FormControl
                  type='text'
                  name='appName'
                  id='appName'
                  onChange={handleChange}
                  value={values.appName}
                />
              </FormGroup>
              <FormGroup>
                <p>Введите описание приложения:</p>
                <FormControl
                  as='textarea'
                  name='appDescription'
                  id='appDescription'
                  onChange={handleChange}
                  isInvalid={!!errors.appDescription}
                  value={values.appDescription}
                />
              </FormGroup>
              <FormGroup>
                <p>Введите стоимость фич-репорта:</p>
                <FormControl
                  type='text'
                  name='featurePrice'
                  id='featurePrice'
                  onChange={handleChange}
                  isInvalid={!!errors.featurePrice}
                  value={values.featurePrice}
                />
              </FormGroup>
              <FormGroup>
                <p>Введите стоимость баг-репорта:</p>
                <FormControl
                  type='text'
                  name='bugPrice'
                  id='bugPrice'
                  onChange={handleChange}
                  isInvalid={!!errors.bugPrice}
                  value={values.bugPrice}
                />
              </FormGroup>
              <FormGroup className='d-flex gap-3 aligns-items-center'>
                <FormCheckLabel htmlFor='available'>
                  Сделать приложение видимым для других:
                </FormCheckLabel>
                <Form.Check
                  type='checkbox'
                  id='available'
                  name='available'
                  checked={values.available}
                  onChange={handleChange}
                />
              </FormGroup>
              <FormGroup>
                <p>Введите ссылку на изображение приложения:</p>
                <FormControl
                  type='text'
                  name='iconImage'
                  id='iconImage'
                  onChange={handleChange}
                  isInvalid={!!errors.iconImage}
                  value={values.iconImage}
                />
              </FormGroup>
              <FormGroup>
                <p>Введите ссылку для скачивания приложения:</p>
                <FormControl
                  type='text'
                  name='downloadLink'
                  id='downloadLink'
                  onChange={handleChange}
                  isInvalid={!!errors.downloadLink}
                  value={values.downloadLink}
                />
              </FormGroup>
              <Button type='submit'>Добавить</Button>
            </Form>
          )}
        </Formik>
      </div>
    </main>
  );
};

export default NewAppPage;
