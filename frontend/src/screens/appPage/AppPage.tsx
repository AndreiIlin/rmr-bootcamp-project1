import React from 'react';
import { Container } from 'react-bootstrap';
import AboutApp from '../../componenst/aboutApp';
import { useParams } from 'react-router-dom';
import { useGetAppQuery } from '../../store/api/appsApiSlice/appsApiSlice';
import TabsSection from '../../componenst/tabsSection';

const AppPage = () => {
  const { id } = useParams();
  const { data, isLoading } = useGetAppQuery(id as string);

  return (
    <Container className="wrapper bg-dark text-light my-5 p-5 shadow shadow-lg rounded-3">
      <AboutApp data={data} isLoading={isLoading} />
      <TabsSection data={data} />
    </Container>
  );
};

export default AppPage;
