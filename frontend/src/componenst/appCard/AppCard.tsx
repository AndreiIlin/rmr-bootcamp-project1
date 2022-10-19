import React, { FC } from 'react';
import { Card } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { routes } from '../../utils/routes';

interface AppCardProps {
  id: string;
  appName: string;
  iconImage: string;
  description?: string;
}

const AppCard: FC<AppCardProps> = ({ appName, iconImage, id, description }) => {
  const navigate = useNavigate();
  const clickHandler = (): void => {
    navigate(routes.pages.appPagePath(id));
  };
  return (
    <Card className="col-6 col-sm-3 col-lg-2 bg-transparent border-0">
      <Card.Body className="d-flex align-items-center flex-column">
        <Card.Img
          src={iconImage}
          alt={appName}
          title={description}
          className="img-custom"
          onClick={clickHandler}
        />
        <Card.Text className="text-light text-custom mt-3 text-truncate">{appName}</Card.Text>
      </Card.Body>
    </Card>
  );
};

export default AppCard;
