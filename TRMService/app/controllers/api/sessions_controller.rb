class Api::SessionsController < Devise::SessionsController
  include Devise::Controllers::Helpers
  prepend_before_filter :require_no_authentication, :only => [:create]
  #Skip authenication
  skip_before_action :verify_authenticity_token


  #POST /api/users/sign_in
  #Check sign in params and make a sign in token
  def create
    current_user = User.find_for_database_authentication(:email => sign_in_params[:email])
    if current_user.nil?
      return failure unless resource
    end
    if current_user.valid_password?(params[:user][:password])
      token = Devise.friendly_token
      current_user.update_attribute :current_sign_in_token, token
        render :json => {:success => true, :id => current_user.id, :token => current_user.current_sign_in_token}
    else
      render :json => {success: false, message: 'Password is not correct!'}
    end
  end

  #DELETE /api/users/sign_out
  #Clear sign in token
  def destroy
    #TODO destroy: User sign out
  end

  #Response failure if wrong email and password
  def failure
    return render json: {success: false, message: 'This user does not exist'}, :status => :unauthorized
  end

  private
  #Get signIn params from clinet
  def sign_in_params
    params.require(:user).permit(:email, :password) if params[:user]
  end

end