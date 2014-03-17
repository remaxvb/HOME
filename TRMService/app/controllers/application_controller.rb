class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  #Will log out the current user if another instance of the current user logs in.
  #This will ensure that only one session is active for a user at any instance of time.
  #before_action :current_user_signed_in, :unless => Proc.new { |c| c.controller_name == 'sessions' and c.action_name = 'create' }

  def current_user_signed_in
    current_user=User.find(user_authenication[:id])
    if user_authenication[:token].nil?
      render :json => {success: false, message: 'You did not sign in before'}
    elsif current_user && user_authenication[:token] != current_user.current_sign_in_token
      render :json => {success: false, message: 'User already signed on in another device'}
    end
  end

  private
  def user_authenication
    params.require(:user).permit(:id, :token) if params[:user]
  end

end
