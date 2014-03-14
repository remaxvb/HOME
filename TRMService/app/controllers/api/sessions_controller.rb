class Api::SessionsController < Devise::SessionsController
  include Devise::Controllers::Helpers
  prepend_before_filter :require_no_authentication, :only => [:create]
  #Skip authenication
  skip_before_action :verify_authenticity_token

  def create
    currentuser = User.find_for_database_authentication(:email => params[:user][:email])
    if currentuser.nil?
      return failure unless resource
    end
    if currentuser.valid_password?(params[:user][:password])
      if (currentuser.login_token != nil)&&(session[:token] == currentuser.login_token)
        sign_fail
      else
        token = Devise.friendly_token
        session[:token] = token
        currentuser.login_token = token
        currentuser.save
        render :json => {:success => true}
      end
    end
  end

  def destroy
    Devise.sign_out_all_scopes ? sign_out : sign_out(resource_name)
    render :status => 200, :json => {}
  end

  def failure
    return render json: {success: false, errors: ['Wrong email or password']}, :status => :unauthorized
  end

  def sign_fail
    render :json => {:success => false, errors: 'Your account already signed in before'}
  end
end