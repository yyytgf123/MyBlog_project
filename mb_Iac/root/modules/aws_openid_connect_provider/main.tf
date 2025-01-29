resource "aws_iam_openid_connect_provider" "eks_oidc_provider" {
  client_id_list = var.client_id_list
  url = var.url
  thumbprint_list = ["990F4193972F2BECF12DDEDA5237F9C952F20D9E"]  # AWS>의 OIDC thumbprint
}