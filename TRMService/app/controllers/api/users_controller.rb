class Api::UsersController < ApplicationController
  #Skip authenication
  skip_before_action :verify_authenticity_token

  # POST /api/users/sign_up
  def signup

  end

  #POST /api/users/sign_in
  def signin

  end

  #DELETE /api/users/sign_out
  def signout

  end

  #POST /api/users/user_info
  def userinfo

  end

  #POST /api/users/change_password
  def changepassword

  end

  #Get signup param from client
  def signup_params
    params.require(:user).permit(:firstname, :lastname, :email, :password,
    :address, :phone, :dayofbirth, :gender) if params[:user]
  end

end
