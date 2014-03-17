class Api::RegistrationsController < ApplicationController
  #Skip authenication
  skip_before_action :verify_authenticity_token, :current_user_signed_in

  # POST /api/users/sign_up
  # Sign up new account
  def create
    user = User.new(signup_params)
    if user.save
      render :json => {:success => true, :message => user.as_json}
    else
      render :json => {:success => false, :message => user.errors}
    end
  end

  private

  #Get sign up params from client
  def signup_params
    params.require(:user).permit(:firstname, :lastname, :email, :password,
                                 :address, :phone, :date_of_birth, :gender) if params[:user]
  end

end
