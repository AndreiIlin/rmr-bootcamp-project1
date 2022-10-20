import React, { FC, useState } from 'react';
import { Button, Container, Form, InputGroup, Row } from 'react-bootstrap';
import { useTranslation } from 'react-i18next';
import { AiOutlineSearch } from 'react-icons/ai';
import AppCard from '../../componenst/appCard';
import { useGetAppsQuery } from '../../store/api/appsApiSlice/appsApiSlice';

const MainPage: FC = () => {
  const { t } = useTranslation();
  const [filter, setFilter] = useState('');
  const { data } = useGetAppsQuery({
    page: 0,
    size: 10,
    filter,
  });

  const [search, setSearch] = useState('');

  return data?.length ? (
    <Container
      as="main"
      className="vh-100 px-0 d-flex flex-column flex-wrap justify-content-start align-content-start"
    >
      <Row xs={5} className="w-100">
        <Form
          className="my-3 m-auto"
          onSubmit={(event) => {
            event.preventDefault();
            setFilter(search);
          }}
        >
          <InputGroup>
            <Form.Control
              type="search"
              className="ps-5 py-2"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
            <Button variant="outline-light" type="submit">
              <AiOutlineSearch />
            </Button>
          </InputGroup>
        </Form>
      </Row>
      <Row className="w-100">
        {data?.map((item) => (
          <AppCard
            key={item.id}
            iconImage={item.iconImage}
            description={item.appDescription}
            appName={item.appName}
            id={item.id}
          />
        ))}
      </Row>
    </Container>
  ) : (
    <Container
      fluid
      as="main"
      className="vh-100 px-0 d-flex flex-column flex-wrap justify-content-start text-center"
    >
      <Row xs={5} className="w-100">
        <Form
          className="my-3 m-auto"
          onSubmit={(event) => {
            event.preventDefault();
            setFilter(search);
          }}
        >
          <InputGroup>
            <Form.Control
              type="search"
              className="ps-5 py-2"
              value={search}
              onChange={(e) => setSearch(e.target.value)}
            />
            <Button variant="outline-light" type="submit">
              <AiOutlineSearch />
            </Button>
          </InputGroup>
        </Form>
      </Row>
      <Row className="w-100">
        <p className="display-5 text-light">{t('appsFields.notFoundApps')}</p>
      </Row>
    </Container>
  );
};

export default MainPage;
