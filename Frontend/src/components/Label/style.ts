import styled, { css } from 'styled-components';

import { LabelCustomStyleProps, LabelProps } from '@/components/Label/index';

const getLabelInnerColorStyle = (color: string) => `
  color: ${color};
  svg > * {
    stroke: ${color};
  }
`;

const large = css`
  width: 100px;
  height: 40px;
  border-radius: 30px;
  font-weight: 500;
  font-size: 12px;
  line-height: 20px;
`;

const small = css`
  width: 90px;
  height: 28px;
  border-radius: 30px;
  font-weight: 500;
  font-size: 12px;
  line-height: 20px;
`;

const openStyle = css`
  border: 1px solid ${({ theme }) => theme.colors.blueScale.blue};
  background: ${({ theme }) => theme.colors.blueScale.lightBlue};
  ${({ theme }) => getLabelInnerColorStyle(theme.colors.blueScale.blue)}
`;

const closeStyle = css`
  border: 1px solid ${({ theme }) => theme.colors.purpleScale.purple};
  background: ${({ theme }) => theme.colors.purpleScale.lightPurple};
  ${({ theme }) => getLabelInnerColorStyle(theme.colors.purpleScale.purple)}
`;

const lightStyle = css`
  color: ${({ theme }) => theme.colors.greyScale.offWhite};
`;

const darkStyle = css`
  color: ${({ theme }) => theme.colors.greyScale.titleActive};
`;

const lineStyle = css`
  color: ${({ theme }) => theme.colors.greyScale.label};
  border: 1px solid ${({ theme }) => theme.colors.greyScale.line};
`;

const createSize = (size: string) => {
  if (size === 'large') return large;
  if (size === 'small') return small;
  return '';
};

const createStyle = (style: string) => {
  if (style === 'open') return openStyle;
  if (style === 'close') return closeStyle;
  if (style === 'dark') return darkStyle;
  if (style === 'light') return lightStyle;
  if (style === 'line') return lineStyle;
  return '';
};

const createCustomStyle = (props: LabelCustomStyleProps) => css`
  ${props.color && getLabelInnerColorStyle(props.color)}
  ${props.background && { background: props.background }}
  ${props.border && { border: props.border }}
  ${props.fontSize && { 'font-size': props.fontSize }}
  ${props.fontWeight && { 'font-weight': props.fontWeight }}
`;

export const Label = styled.button<LabelProps>`
  ${({ theme }) => theme.mixins.flexBox('row', 'center', 'center')}
  ${({ size }) => createSize(size)}
  ${({ labelStyle }) => createStyle(labelStyle)}
  ${({ ...props }) => createCustomStyle(props)}
  svg {
    margin-right: 6px;
  }
`;

export const Contents = styled.div``;
