import React, { FC } from 'react';
import { Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { routes } from '../../../utils/routes';

interface ShortCardProps {
  id: string;
  appName: string;
  iconImage: string;
}

const ShortAppCard: FC<ShortCardProps> = ({ appName, iconImage, id }) => {
  const navigate = useNavigate();
  const clickHandler = (): void => {
    navigate(routes.pages.appPagePath(id));
  };
  return (
    <Card
      style={{ width: '150px', height: '150px', cursor: 'pointer', border: 'none' }}
      className='gap-3'
      onClick={clickHandler}>
      <Card.Body className='d-flex align-items-center flex-column flex-wrap'>
        <Card.Img src={iconImage} alt={appName} style={{ width: '70px', height: '70px' }} />
        <Card.Body>{appName}</Card.Body>
      </Card.Body>
    </Card>
  );
};

export default ShortAppCard;
