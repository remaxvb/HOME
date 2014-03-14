class Api::SessionsController < Devise::SessionsController
  #prepend_before_filter :require_no_authentication, :only => [:create ]
  #include Devise::Controllers::InternalHelpers
  skip_before_action :verify_authenticity_token
  before_filter :ensure_params_exist
  respond_to :json
  def create
    resource_class.new
    resource = User.find_for_database_authentication(:username=>params[:user][:username])
    return invalid_login_attempt unless resource
    if resource.valid_password?(params[:user][:password])
      #sign_in("user", resource)
      render :json=> {:success=>true,:fullname=>resource}
    return
    end
    invalid_login_attempt
  end

  # def destroy
  # sign_out(resource_name)
  # end

  protected

  def ensure_params_exist
    return unless params[:user].blank?
    render :json=>{:success=>false, :message=>"Missing user_login parameter"}, :status=>422
  end

  def login_params
    params.require(:user).permit(:username) if params[:user]
  end

  def invalid_login_attempt
    warden.custom_failure!
    render :json=> {:success=>false, :message=>"Error with your login or password"}, :status=>401
  end
end