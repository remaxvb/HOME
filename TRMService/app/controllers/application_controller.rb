class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :exception

  #Will log out the current user if another instance of the current user logs in.
  #This will ensure that only one session is active for a user at any instance of time.
  before_action :invalidate_simultaneous_user_session, :unless => Proc.new {|c| c.controller_name == 'sessions' and c.action_name = 'create' }

  def invalidate_simultaneous_user_session
    sign_out_and_redirect(current_user) if current_user && session[:sign_in_token] != current_user.current_sign_in_token
  end

  def sign_in(resource_or_scope, *args)
    super
    token = Devise.friendly_token
    current_user.update_attribute :current_sign_in_token, token
    session[:sign_in_token] = token
  end

end
