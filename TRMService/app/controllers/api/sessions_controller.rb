class Api::SessionsController < Devise::SessionsController
  include Devise::Controllers::Helpers
  prepend_before_filter :require_no_authentication, :only => [:create]
  #Skip authenication
  skip_before_action :verify_authenticity_token

  def create
    current_user = User.find_for_database_authentication(:email => params[:user][:email])
    if current_user.nil?
      return failure unless resource
    end
    if current_user.valid_password?(params[:user][:password])
      sign_in(current_user)
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