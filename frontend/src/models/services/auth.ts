export interface AuthRequest {
  email: string;
  password: string;
  role?: string;
}

export interface AuthResponse {
  user_id: string;
  access_token: string;
}
